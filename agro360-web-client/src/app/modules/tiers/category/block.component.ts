import { Component, Input, OnInit } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { CategoryBean } from 'src/app/backed/bean.tiers';
import { CategoryBlockComponent } from '../tiers/category.block.component';
import { InputTextFieldComponent } from 'src/app/common/field/input.text';
import { InputTextareaFieldComponent } from 'src/app/common/field/input.textarea';
import { InputEmailFieldComponent } from 'src/app/common/field/input.email';
import { InputTelFieldComponent } from 'src/app/common/field/input.tel';
import { SelectOneFieldComponent } from 'src/app/common/field/select.one';
import { CommonModule } from '@angular/common';

@Component({
    standalone: true,
    imports:[
        CommonModule,
        MatIconModule,             
        CategoryBlockComponent,
        InputTextFieldComponent,
        InputTextareaFieldComponent,
        InputEmailFieldComponent,
        InputTelFieldComponent,
        SelectOneFieldComponent
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
