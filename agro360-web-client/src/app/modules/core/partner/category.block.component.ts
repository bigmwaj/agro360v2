import { Component, Input, OnInit } from '@angular/core';
import { PartnerCategoryBean } from 'src/app/backed/bean.core';
import { SharedModule } from 'src/app/common/shared.module';

@Component({
    standalone: true,
    imports:[
        SharedModule
    ],
    selector: 'core-partner-category-block',
    templateUrl: './category.block.component.html'
})
export class CategoryBlockComponent implements OnInit {

    @Input()
    bean: PartnerCategoryBean;

    ngOnInit(): void {

    }

}
