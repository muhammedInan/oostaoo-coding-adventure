import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { MatSnackBar } from '@angular/material';
import { ApiClientService, API_URI_FEEDBACK } from 'src/app/api-client/api-client.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-fin-test',
  templateUrl: './fin-test.component.html',
  styleUrls: ['./fin-test.component.scss']
})
export class FinTestComponent implements OnInit {
  public myStars = ['Très mauvaise', 'Mauvaise', 'Moyenne', 'Bonne', 'Très bonne'];
  isSelected: boolean;
  rating: number;
  public commentaire = new FormControl("");
  constructor(public apiClientService: ApiClientService, private _snackBar: MatSnackBar, private toastr: ToastrService) { }

  ngOnInit() {
  }

  openSnackBar(message: string, action) {
    this._snackBar.open(message, action, {
      duration: 3000,
    });
  }

  showSuccess(message) {
    this.toastr.success(message);
  }
  showError(message) {
    this.toastr.info(message);
  }

  getRating(rate: number) {
    this.rating = rate;
  }

  sendFeedback() {
    console.log(this.rating)
    if(this.rating === undefined) {
      return this.showError("Veuillez mettre une note pour faire votre retour");
    }
    this.apiClientService.post(API_URI_FEEDBACK, {
      rating: this.rating,
      commentaires: this.commentaire.value,
    }).subscribe(
      (res) => {
        this.showSuccess("Nous vous remercions pour votre retour");
      },
      err => console.log(err)
    );
  }

}