import { HttpClient } from '@angular/common/http';
import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { TiersCategoryBean } from 'src/app/backed/bean.tiers';

@Component({
    selector: 'app-tiers-category-block',
    templateUrl: './category.block.component.html'
})
export class CategoryBlockComponent implements OnInit {

    @Input()
    bean: TiersCategoryBean;

    constructor(private router: Router,
        private route: ActivatedRoute,
        private http: HttpClient) { }

    ngOnInit(): void {

    }

}
