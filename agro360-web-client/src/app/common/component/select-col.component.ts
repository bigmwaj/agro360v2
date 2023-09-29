import { Component, Input } from '@angular/core';
import { BeanList } from './bean.list';
@Component({
    selector: 'select-col',
    templateUrl: './select-col.component.html'
})
export class SelectColComponent {

    @Input()
    beanList: BeanList<any>;
}
