import { Component, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-alert',
  templateUrl: './alert-modal.component.html'
})
export class AlertModalComponent implements OnInit {

  title:string;
  
  message:string;

  constructor(public activeModal: NgbActiveModal) { }

  ngOnInit(): void {
  }

  setMessages(messages:string[]){   
      const message = messages.map(e => `<li>${e}</li>`).reduce((e1, e2) => e1 + e2, "")
      this.message = `<ul>${message}</ul>`;
  }
}
