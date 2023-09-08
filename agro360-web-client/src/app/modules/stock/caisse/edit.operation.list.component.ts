import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, Input } from '@angular/core';
import { OperationCaisseBean } from 'src/app/backed/bean.stock';
import { FieldMetadata } from 'src/app/backed/metadata';
import { TypeOperationEnumVd } from 'src/app/backed/vd.stock';
import { AbstractEditLigneListComponent } from 'src/app/common/abstract.edit.ligne.list.component';
import { SharedModule } from 'src/app/common/shared.module';

@Component({
    standalone: true,
    imports: [
        SharedModule
    ],
    selector: 'stock-edit-operation-list',
    templateUrl: './edit.operation.list.component.html'
})
export class EditOperationListComponent extends AbstractEditLigneListComponent<OperationCaisseBean> {
    
    displayedColumns: string[] = [
        'select',
        'numero',
        'dateOperation',
        'typeLigne',
        'article',
        'variantCode',
        'typeOperation',
        'quantite',
        'unite',
        'prixUnitaire',
        'prixTotal',
        'actions'
    ];

    @Input()
    plusAchetes:FieldMetadata<string>;

    @Input()
    plusVendus: FieldMetadata<string>;

    constructor(public override http: HttpClient) {
        super(http);
    }
    
    getCreateFormUrl():string{
        return '/stock/caisse/operation/create-form';
    }

    plusVenduSelectEvent() {
        if( this.plusVendus.value == null ){
            return
        }        
        let queryParams = new HttpParams();
        queryParams = queryParams.appendAll(this.ownerId);
        queryParams = queryParams.append('articleCode', this.plusVendus.value);        
        queryParams = queryParams.append('typeOperation', TypeOperationEnumVd.RECETTE);
        this.__add(queryParams);
        
        this.plusVendus.value = '';
    }

    plusAcheteSelectEvent() {      

        if( this.plusAchetes.value == null ){
            return
        }        
        let queryParams = new HttpParams();
        queryParams = queryParams.appendAll(this.ownerId);
        queryParams = queryParams.append('articleCode', this.plusAchetes.value);
        queryParams = queryParams.append('typeOperation', TypeOperationEnumVd.DEPENSE);
        this.__add(queryParams);
        
        this.plusAchetes.value = '';
    }
}
