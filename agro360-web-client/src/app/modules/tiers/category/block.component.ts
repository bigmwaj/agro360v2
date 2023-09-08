import { Component, Input, OnInit } from '@angular/core';
import { CategoryBean } from 'src/app/backed/bean.tiers';
import { CategoryBlockComponent } from '../tiers/category.block.component';
import { SharedModule } from 'src/app/common/shared.module';

@Component({
    standalone: true,
    imports:[           
        CategoryBlockComponent,
        SharedModule
    ],
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
