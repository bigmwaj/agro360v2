import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { AuthStatusModel } from '../../models/auth-status-model';
import { UtilsService } from '../../services/utils.service';

interface ChangeStatusForm{
  id?:{} | string | number
	memo?:string;
	changedDate?:any;
	changedTime?:any;
	status?:string;
}

@Component({
  selector: 'app-change-status',
  templateUrl: './change-status-modal.component.html'
})
export class ChangeStatusModalComponent implements OnInit {
  title:string;
  form:ChangeStatusForm = {};
  authStatuses:AuthStatusModel[];
  url:string;
  callback:(p:any) => any;

  constructor(
    public activeModal: NgbActiveModal,
    private http:HttpClient,     
    private utils:UtilsService
  ) 
  { }

  ngOnInit(): void {
  }

  process(){
    return this.http.put<any>(this.url, this.form)
      .subscribe(p => { 
        this.utils.notify(this.title, "Succès");
        this.activeModal.close();
        if( null != this.callback ){
          this.callback(p);
        }
      });
  }

}
