import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, Input } from '@angular/core';
import { LigneBean } from 'src/app/backed/bean.achat';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatDialogModule } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatTableModule } from '@angular/material/table';
import { SharedModule } from 'src/app/common/shared.module';
import { TypeOperationEnumVd } from 'src/app/backed/vd.stock';
import { FieldMetadata } from 'src/app/backed/metadata';
import { AbstractEditLigneListComponent } from 'src/app/common/component/abstract.edit.ligne.list.component';

@Component({
    standalone: true,
    imports: [
        CommonModule,
        MatButtonModule,
        MatIconModule,
        MatDialogModule,
        MatCheckboxModule,
        MatTableModule,
        SharedModule
    ],
    selector: 'achat-edit-ligne-list',
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
        'description',
        'actions'
    ];

    constructor(public override http: HttpClient) {
        super(http)
    }
    
    @Input()
    plusAchetes:FieldMetadata<string>;

    override getCreateFormUrl(): string {
        return 'achat/bon-commande/ligne/create-form';
    };

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
