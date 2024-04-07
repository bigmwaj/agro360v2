import { Component, Input, OnInit } from '@angular/core';
import { CategoryBlockComponent } from '../partner/category.block.component';
import { SharedModule } from 'src/app/common/shared.module';
import { CategoryBean } from 'src/app/backed/bean.core';

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
