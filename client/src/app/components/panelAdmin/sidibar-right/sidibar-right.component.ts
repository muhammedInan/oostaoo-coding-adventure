import { Component, OnInit, Input, ViewChild, ElementRef } from '@angular/core';
import { element } from '@angular/core/src/render3';

@Component({
  selector: 'app-sidibar-right',
  templateUrl: './sidibar-right.component.html',
  styleUrls: ['./sidibar-right.component.scss']
})
export class SidibarRightComponent implements OnInit {
  @Input() campaignsFromParent;
  @ViewChild('option') option: ElementRef;
  @ViewChild('check1') check1: ElementRef;
  @ViewChild('check2') check2: ElementRef;

  public Isactive = false;
  public candidatbydate: Array<any> = [];
  public myArrayCandidat: any[] = [];
  public optionFilter: string[];
  constructor() { }

  ngOnInit() {

    for (const campaign of this.campaignsFromParent) {
      for (const candidat of campaign.candidats) {
        this.myArrayCandidat.push(candidat);
        this.candidatbydate = this.myArrayCandidat.sort((a, b) => {
          if (a.invitation_date < b.invitation_date) {
            return 1;
          } else if (b.invitation_date < a.invitation_date) {
            return -1;
          } else {
            return 0;
          }
        });

        console.log('candidat', this.myArrayCandidat)
      }
    }
  }


  public param_cog() {

    this.Isactive = true;

  }

  public param_cog_non_active() {

    this.Isactive = false;

  }

  public hundeleSubmit() {

    if (this.check1.nativeElement.checked && this.check2.nativeElement.checked) {

      this.candidatbydate = this.myArrayCandidat;

    } else {

      if (!this.check2.nativeElement.checked && !this.check2.nativeElement.checked) {
        this.candidatbydate = []
      }

      if (this.check1.nativeElement.checked) {

        if (this.candidatbydate.length === 0) {
          this.candidatbydate = this.myArrayCandidat
        }

        this.candidatbydate = this.candidatbydate.filter(element => {
          return element.invitation_date == element.test_terminer;
        })


      }

      if (this.check2.nativeElement.checked) {

        if (this.candidatbydate.length === 0) {
          this.candidatbydate = this.myArrayCandidat
        }

        this.candidatbydate = this.candidatbydate.filter(element => {
          return element.invitation_date !== element.test_terminer;
        })

      }

    }

    this.Isactive = false;

  }

}
