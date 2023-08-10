import { Component, Input, OnInit } from '@angular/core';
import { CategoryBean } from 'src/app/backed/bean.tiers';

@Component({
    selector: 'tiers-category-category-block',
    templateUrl: './block.component.html'
})
export class BlockComponent implements OnInit {

    @Input()
    bean: CategoryBean;

    constructor() { }

    ngOnInit(): void {

    }

}
