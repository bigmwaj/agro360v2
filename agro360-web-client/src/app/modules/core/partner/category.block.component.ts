import { Component, Input, OnInit } from '@angular/core';
import { MatTooltipModule } from '@angular/material/tooltip';
import { PartnerCategoryBean } from 'src/app/backed/bean.core';
import { SharedModule } from 'src/app/modules/common/shared.module';

@Component({
    standalone: true,
    imports:[
        SharedModule,
        MatTooltipModule
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
