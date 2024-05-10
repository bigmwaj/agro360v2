import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { map } from 'rxjs';
import { CommandeBean, FactureBean, ReglementBean } from 'src/app/backed/bean.av';
import { FieldMetadata } from 'src/app/backed/metadata';
import { SharedModule } from 'src/app/modules/common/shared.module';

@Component({
    standalone: true,
    imports: [
        SharedModule
    ],
    selector: 'achat-vente-reglement-dialog',
    templateUrl: './reglement.dialog.component.html',
})
export class ReglementDialogComponent implements OnInit {

    reglements: ReglementBean[] = []

    bean: FactureBean | CommandeBean

    ref:FieldMetadata<string>

    displayedColumns: string[] = [        
        'type',
        'code',
        'status',
        'date',
        'compte',
        'montant',
    ];

    constructor(
        @Inject(MAT_DIALOG_DATA) public data: {url:string, queryParams:HttpParams, bean:FactureBean | CommandeBean},
        private http: HttpClient,
        public dialogRef: MatDialogRef<ReglementDialogComponent>) { }

    ngOnInit(): void {    
        this.bean = this.data.bean;    
        const _bean: any = this.bean;
        
        if( _bean.factureCode ){
            this.ref = (<FactureBean>this.bean).factureCode
        }else{
            this.ref = (<CommandeBean>this.bean).commandeCode
        }
        
        this.http
            .get(this.data.url, { params: this.data.queryParams })
            .pipe(map((data: any) => <ReglementBean[]>data))
            .subscribe(data => {
                this.reglements = data;
            });
    }
}
