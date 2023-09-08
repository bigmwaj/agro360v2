import { Component, Input, OnInit } from '@angular/core';
import { TiersCategoryBean } from 'src/app/backed/bean.tiers';
import { SharedModule } from 'src/app/common/shared.module';

@Component({
    standalone: true,
    imports:[
        SharedModule
    ],
    selector: 'tiers-tiers-category-block',
    templateUrl: './category.block.component.html'
})
export class CategoryBlockComponent implements OnInit {

    @Input()
    bean: TiersCategoryBean;

    ngOnInit(): void {

    }

}
