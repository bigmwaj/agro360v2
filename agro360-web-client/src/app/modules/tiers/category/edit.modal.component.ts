import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { map } from 'rxjs';
import { CategoryBean } from 'src/app/backed/bean.tiers';

const BASE_URL = "http://localhost:8080/tiers/category";

@Component({
    selector: 'tiers-category-edit-modal',
    templateUrl: './edit.modal.component.html'
})
export class EditModalComponent implements OnInit {

    bean: CategoryBean;

    constructor( private http: HttpClient) { }

    ngOnInit(): void {
        this.http
            .get(BASE_URL + '')
            .pipe(map((data: any) => data))
            .subscribe(data => this.bean = <CategoryBean>data);
    }

}
