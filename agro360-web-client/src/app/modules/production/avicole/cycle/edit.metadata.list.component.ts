import { Component, Input, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { MatTable } from '@angular/material/table';
import { MetadataBean } from 'src/app/backed/bean.production.avicole';
import { BeanList } from 'src/app/common/component/bean.list';
import { SharedModule } from 'src/app/common/shared.module';

import { MatPaginator } from '@angular/material/paginator';

@Component({
    standalone: true,
    imports: [
        SharedModule
    ],
    selector: 'production-avicole-edit-metadata-list',
    templateUrl: './edit.metadata.list.component.html'
})
export class EditMetadataListComponent extends BeanList<MetadataBean> implements OnInit, AfterViewInit {

    getKeyLabel(bean: MetadataBean): string {
        return bean.metadataCode.value;
    }

    @Input()
    cycleCode: string;

    @Input()
    metadatas: Array<MetadataBean>;

    @ViewChild(MatTable)
    table: MatTable<MetadataBean>;

    @ViewChild(MatPaginator) __paginator: MatPaginator;

    bean: MetadataBean;

    displayedColumns: string[] = [
        'libelle',
        'value',
        'actions'
    ];

    constructor() {
        super()
    }

    override getViewChild(): MatTable<MetadataBean> {
        return this.table;
    }

    ngOnInit(): void {
        this.setData(this.metadatas);
    }
    
    ngAfterViewInit() {
        this.paginator = this.__paginator;
    }

    showDetailsAction(bean: MetadataBean){
        this.bean = bean
    }
}
