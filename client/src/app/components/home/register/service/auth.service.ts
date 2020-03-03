import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { BehaviorSubject } from 'rxjs';
import { API_URI_CAMPAIGNS, ApiClientService, API_URI_USER, API_URI_USERS_BY_ADMIN } from './../../../../api-client/api-client.service';

// authentication service is used to LOGIN and LOGOUT of the application
// it posts the creds (username and password) to the backend and check for the response if it has JWT token
// if the response from the backend has jwt token, then the authentication was succesful
// on successful authentication, the user details are stored in the local storage + jwt token


@Injectable({ providedIn: 'root' })
export class AuthenticationService {
  currentUserSubject: BehaviorSubject<any>;
  constructor(private http: HttpClient, public apiClientService: ApiClientService) {
    this.currentUserSubject = new BehaviorSubject(localStorage.getItem('currentUser'));
  }

  public get currentUserValue() {
    this.currentUserSubject = new BehaviorSubject(localStorage.getItem('currentUser'));
    console.log('currentUserSubject : ', this.currentUserSubject);
    return this.currentUserSubject.value;
  }

  // login
  login(identifier: string, password: string) {
    return this.http.post<any>(`/auth/local`, { identifier, password })
      .pipe(
        // the backend service sends an instance of the user
        // user: any (because .post<any>)
        map(user => {
          console.log('AUTH SERVICE user: ', user);
          // login successful if the response has jwt token
          if (user && user.jwt) {
            // store user details and jwt token in the local storage to keep the user logged in between page refreshes
            localStorage.setItem('currentUser', user.jwt);
            this.currentUserSubject.next(user);
          }
          return user;
        })
      );
  }

  register(username: string, email: string, password: string) {
    return this.http.post<any>(`/auth/local/register`, {
      username,
      email,
      password
    })
      .pipe(
        map(user => {
          console.log('AUTH SERVICE user: ', user);
          // login successful if the response has jwt token
          if (user && user.jwt) {
            // store user details and jwt token in the local storage to keep the user logged in between page refreshes
            localStorage.setItem('currentUser', user.jwt);
            this.currentUserSubject.next(user);
          }
          return user;
        })
      );
  }

  forgotPassword(email: string) {
    return this.http.post<any>(`/auth/forgot-password`, {
      email
    })
      .pipe(
        map(user => {
          console.log('FORGOT PASSWORD user: ', user);
          return user;
        })
      );
  }

  // logout
  logout() {
    // remove user from local storage
    localStorage.removeItem('currentUser');
  }

  getCampaignsUser(adminId: number) {
    return this.apiClientService.get(API_URI_CAMPAIGNS + '?user_in=' + adminId).toPromise();
  }

  getUsers(adminId: number) {
    return this.apiClientService.get(`${API_URI_USERS_BY_ADMIN}/${adminId}`).toPromise();
  }
}
