import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, Input } from '@angular/core';
import { LigneBean } from 'src/app/backed/bean.vente';
import { FieldMetadata } from 'src/app/backed/metadata';
import { TypeOperationEnumVd } from 'src/app/backed/vd.stock';
import { AbstractEditLigneListComponent } from 'src/app/common/abstract.edit.ligne.list.component';
import { SharedModule } from 'src/app/common/shared.module';

@Component({
    standalone: true,
    imports: [
        SharedModule
    ],
    selector: 'vente-edit-ligne-list',
    templateUrl: './edit.ligne.list.component.html'
})
export class EditLigneListComponent extends AbstractEditLigneListComponent<LigneBean> {
    
    displayedColumns: string[] = [
        'select',
        'numero',
        'typeLigne',
        'article',
        'unite',
        'variantCode',
        'quantite',
        'prixUnitaire',
        'prixTotal',
        'description',
        'actions'
    ];

    constructor(public override http: HttpClient) {
        super(http)
    }

    @Input()
    plusVendus: FieldMetadata<string>;

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
    
    getCreateFormUrl():string{
        return '/vente/commande/ligne/create-form';
    };
}
