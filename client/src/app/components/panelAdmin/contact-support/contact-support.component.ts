import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { ApiClientService, API_URI_ISSUE } from 'src/app/api-client/api-client.service';
import { FormControl, Validators } from '@angular/forms';
import { DecryptTokenService } from '../../home/register/register.service';

@Component({
  selector: 'app-contact-support',
  templateUrl: './contact-support.component.html',
  styleUrls: ['./contact-support.component.scss']
})
export class ContactSupportComponent implements OnInit {

  constructor(public apiClientService: ApiClientService,
    private toastr: ToastrService,
    public decryptTokenService: DecryptTokenService) { }

  submittedForm = false;
  prenom = new FormControl('', Validators.required);
  nom = new FormControl('', Validators.required);
  email = new FormControl('', [Validators.required, Validators.email]);
  subject = new FormControl('', Validators.required);
  message = new FormControl('', Validators.required);

  ngOnInit() {
  }

  showSuccess(message) {
    this.toastr.success(message);
  }
  showError(message) {
    this.toastr.info(message);
  }

  sendFormular() {
    this.submittedForm = true;
    if (this.prenom.value === '' || this.nom.value === '' || this.email.value === '' || this.subject.value === '' || this.message.value === '') {
      this.showError('Erreur veuillez correctement remplir tous les champs requis');
      return console.log('Erreur veuillez remplir tout les champs requis');
    } else {
    this.apiClientService.post(API_URI_ISSUE, {
      Prenom: this.prenom.value,
      Nom: this.nom.value,
      email: this.email.value,
      Subject: this.subject.value,
      Message: this.message.value
    }).subscribe(
      (res) => {
        this.showSuccess("Le formulaire a correctement été envoyé à l'équipe de support");
       // console.log('res', res);
      },
      err => console.log(err)
    );
    
 }
}
}
