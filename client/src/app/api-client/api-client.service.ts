import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map, catchError } from 'rxjs/operators';
import { Observable, throwError } from 'rxjs';


const prefix = '/api/';


export const API_URI_CAMPAIGNS: string = prefix + 'campaigns';
export const API_URI_CAMPAIGN: string = prefix + 'campaign/:id';
export const API_URI_TECHNO: string = prefix + 'technologies';
export const API_URI_PROFILES: string = prefix + 'profiles';
export const API_URI_QUESTIONS: string = prefix + 'questions';
export const API_URI_CANDIDATS: string = prefix + 'candidats';
export const API_URI_CANDIDATS_PDF_REPORT: string = prefix + 'candidats-report-pdf/';
export const API_URI_EMAIL: string = prefix + 'email';
export const API_URI_USER_ADMIN: string = prefix + 'utilisateursadmins';
export const API_URI_ENTREPRISE: string = prefix + 'entreprises';
export const API_URI_USER_ENTREPRISE: string = prefix + 'utilsateurentreprises';
export const API_URI_USER = 'users';
export const API_URI_ACCOUNT = prefix + 'customeraccounts';
export const API_URI_ADDRESS = prefix + 'addresses';
export const API_URI_USERS_BY_ADMIN = 'usersByAdmin';
export const API_URI_NOTIFICATIONS: string = prefix + 'notifications';
export const API_URI_OFFER: string = prefix + 'offers';
export const API_URI_PAYMENT: string = prefix + 'payments';
export const API_URI_FEEDBACK: string = prefix + 'feedbacks';
export const API_URI_FAQ: string = prefix + 'faqs';
export const API_URI_ISSUE: string = prefix + 'issues';
export const API_URI_ROLE: string =  'users-permissions/roles';
export const EXECUTE_SCRIPT: string = prefix + 'questions/execute';

//Bp6PnJmoydK5rz6o

@Injectable()

export class ApiClientService {

  constructor(private http: HttpClient) { }

  private handleError(error: any, caught: Observable<any>): Observable<any> {
    let errorMessage = '';
    if (error.error instanceof ErrorEvent) {
      // client-side error
      errorMessage = `Error: ${error.error.message}`;
    } else {
      // server-side error
      errorMessage = error;
    }
    return throwError(errorMessage);
  }

  private extractData(res: Response) {
    const body = res;
    return body || {};
  }



  /**
   * Methode get()
   * @param path
   */
  get(path: string): Observable<any> {
    return this.http.get(path).pipe(map(this.extractData));
  }

  /**
   * Methode post()
   * @param path
   * @param payload
   */
  post(path: string, payload: any): Observable<any> {
    return this.http.post<any>(path, payload).pipe(
      catchError(this.handleError)
    );
  }

  /**
   * Methode delete()
   * @param path
   */
  delete(path: string): Observable<any> {
    return this.http.delete<any>(path).pipe(
      catchError(this.handleError)
    );
  }

  /**
   * Methode put()
   * @param path
   * @param payload
   */
  put(path: string, payload: any): Observable<any> {
    return this.http.put<any>(path, payload).pipe(
      catchError(this.handleError)
    );
  }
  /**
   * Methode put()
   * @param path
   * @param payload
   */
  patch(path: string, payload: any): Observable<any> {
    return this.http.patch<any>(path, payload).pipe(
      catchError(this.handleError)
    );
  }

}
