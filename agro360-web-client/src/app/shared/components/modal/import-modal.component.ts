import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { UtilsService } from '../../services/utils.service';

@Component({
  selector: 'app-import',
  templateUrl: './import-modal.component.html'
})
export class ImportModalComponent implements OnInit {

  title:string;
  fichier:any;
  callback:(p:any) => any;
  url:string;

  constructor(
    public activeModal: NgbActiveModal,
    private http:HttpClient,     
    private utils:UtilsService
  ) { }

  ngOnInit(): void {

  }

  import(formData : NgForm):void{
    this.http.put(this.url, this.fichier)
      .subscribe((p) => { 
        this.utils.notify(this.title, "Succès");
        this.activeModal.close();
        if( this.callback !== null ){
          this.callback(p);
        }
      })
  }

  importModel(){
    this.http.get(this.url).subscribe((p) => {})
  }

}
