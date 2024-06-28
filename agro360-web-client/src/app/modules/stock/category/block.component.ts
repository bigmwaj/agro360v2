import { Component, Input, OnInit } from '@angular/core';
import { SharedModule } from 'src/app/modules/common/shared.module';
import { CategoryBean } from 'src/app/backed/bean.stock';
import { CategoryBlockComponent } from './category.block.component';

@Component({
    standalone: true,
    imports:[           
        CategoryBlockComponent,
        SharedModule
    ],
    selector: 'core-category-block',
    templateUrl: './block.component.html'
})
export class BlockComponent implements OnInit {

    @Input()
    bean: CategoryBean;

    constructor() { }

    ngOnInit(): void {

    }

}
