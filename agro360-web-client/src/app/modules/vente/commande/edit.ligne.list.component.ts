import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { LigneBean } from 'src/app/backed/bean.vente';
import { AbstractEditLigneListComponent } from 'src/app/common/abstract.edit.ligne.list.component';
import { SharedModule } from 'src/app/common/shared.module';
import { MatTableModule } from '@angular/material/table';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatIconModule } from '@angular/material/icon';
import { MatDialogModule } from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';
import { CommonModule } from '@angular/common';

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
    
    getCreateFormUrl():string{
        return '/vente/commande/ligne/create-form';
    };
}
