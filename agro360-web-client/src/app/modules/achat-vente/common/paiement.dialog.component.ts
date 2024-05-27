import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { map } from 'rxjs';
import { CommandeBean, FactureBean, PaiementParamBean } from 'src/app/backed/bean.av';
import { Message } from 'src/app/backed/message';
import { FieldMetadata } from 'src/app/backed/metadata';
import { UIService } from 'src/app/modules/common/service/ui.service';
import { SharedModule } from 'src/app/modules/common/shared.module';
import { PaiementEditComponent } from './paiement.edit.component';

@Component({
    standalone: true,
    imports: [
        SharedModule,
        PaiementEditComponent
    ],
    selector: 'achat-vente-common-paiement-dialog',
    templateUrl: './paiement.dialog.component.html',
})
export class PaiementDialogComponent implements OnInit {

    bean: CommandeBean | FactureBean;

    ref:FieldMetadata<string>

    paiements:Array<PaiementParamBean>

    constructor(
        @Inject(MAT_DIALOG_DATA) public data: {
            bean: CommandeBean | FactureBean, 
            paiements:Array<PaiementParamBean>, 
            saveUrl:string, saveParams:HttpParams
        },
        private http: HttpClient,
        private ui: UIService,  
        public dialogRef: MatDialogRef<PaiementDialogComponent>) { }

    ngOnInit(): void {
        this.bean = this.data.bean;
        this.paiements = this.data.paiements;

        const _bean: any = this.bean;
        
        if( _bean.factureCode ){
            this.ref = (<FactureBean>this.bean).factureCode
        }else{
            this.ref = (<CommandeBean>this.bean).commandeCode
        }
    }

    saveAction() {
        this.http.post(this.data.saveUrl, this.paiements, {params:this.data.saveParams}) 
        .pipe(map((e: any) => <any>e))
        .subscribe(data => {
            this.dialogRef.close(data.record);
            this.ui.displayFlashMessage(<Array<Message>>data.messages);
        })
    }
}
