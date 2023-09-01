import { Component, Input, OnInit } from '@angular/core';
import { TiersCategoryBean } from 'src/app/backed/bean.tiers';
import { MatIconModule } from '@angular/material/icon';
import { InputCheckboxFieldComponent } from 'src/app/common/field/input.checkbox';
import { CommonModule } from '@angular/common';

@Component({
    standalone: true,
    imports:[
        CommonModule,
        MatIconModule,
        InputCheckboxFieldComponent,
    ],
    selector: 'tiers-tiers-category-block',
    templateUrl: './category.block.component.html'
})
export class CategoryBlockComponent implements OnInit {

    @Input()
    bean: TiersCategoryBean;

    constructor() { }

    ngOnInit(): void {

    }

}
