import { Component, Input, OnInit } from '@angular/core';
import { BreadcrumbItemModel } from '../../models/breadcrumb-item-model';

@Component({
  selector: 'app-breadcrumb',
  templateUrl: './breadcrumb.component.html'
})
export class BreadcrumbComponent implements OnInit {

  breadcrumbItems:BreadcrumbItemModel[];

  @Input() items:BreadcrumbItemModel[] | string;

  constructor() { }

  ngOnInit(): void {
    if( typeof this.items == 'string' ){
      this.breadcrumbItems = [{label:this.items, active:true}]
    }else{
      this.breadcrumbItems = this.items;
    }
  }

  processClick(link:BreadcrumbItemModel){
    if( link.click != undefined ){
      link.click()
    }
  }
}
