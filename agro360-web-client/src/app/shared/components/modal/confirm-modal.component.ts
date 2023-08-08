import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { UtilsService } from '../../services/utils.service';

@Component({
  selector: 'app-confirm',
  templateUrl: './confirm-modal.component.html'
})
export class ConfirmModalComponent implements OnInit {
  title:string;
  message:string;
  url:string;
  key:{};//Pour sélectionner l'objet 
  callback:(p?:any) => any;

  constructor(
    public activeModal: NgbActiveModal,
    private http:HttpClient,     
    private utils:UtilsService
  ) { }

  ngOnInit(): void {
  }

  process(){
    return this.http.delete(this.url, {'body':this.key})
      .subscribe(p => { 
        this.utils.notify(this.title, "Succès");
        this.activeModal.close();
        if( null != this.callback ){
          this.callback(p);
        }
      });
  }

}
