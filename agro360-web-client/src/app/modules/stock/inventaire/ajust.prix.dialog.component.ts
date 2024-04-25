import { HttpClient } from '@angular/common/http';
import { Component, Inject, OnInit } from '@angular/core';
import { InventaireBean } from 'src/app/backed/bean.stock';
import { SharedModule } from 'src/app/modules/common/shared.module';
import { MAT_DIALOG_DATA, MatDialog } from '@angular/material/dialog';
import { map } from 'rxjs';
import { UIService } from 'src/app/modules/common/service/ui.service';
import { Message } from 'src/app/backed/message';

@Component({
    standalone: true,
    imports: [
        SharedModule 
    ],
    selector: 'stock-inventaire-ajust-prix-dialog',
    templateUrl: './ajust.prix.dialog.component.html'
})
export class AjustPrixDialogComponent implements OnInit {

    bean: InventaireBean
    constructor(        
        @Inject(MAT_DIALOG_DATA) public data: InventaireBean,
        private http: HttpClient,
        private ui: UIService,
        public dialog: MatDialog) {          
        
    }

    ngOnInit(): void {
        this.bean = this.data
    }

    ajusterAction():void{    
        this.http.post(`stock/inventaire/ajuster-prix`, this.bean)   
            .pipe(map((e: any) => <any>e))
            .subscribe(data => {
                this.ui.displayFlashMessage(<Array<Message>>data.messages);                
                this.bean.prixUnitaireVente.value = data.record.prixUnitaireVente.value;
                this.bean.prixUnitaireVenteAjust.value = 0;
                this.dialog.closeAll();
            });
    }
}
