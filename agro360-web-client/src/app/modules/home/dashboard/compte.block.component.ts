import { Component, OnInit } from '@angular/core';
import { SharedModule } from 'src/app/modules/common/shared.module';
import { map } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { EtatCompteBean } from 'src/app/backed/bean.finance';
import { MatDialog } from '@angular/material/dialog';
import { IndexModalComponent } from '../../finance/compte/index.modal.component';

@Component({
    standalone: true,
    imports: [
        SharedModule, 
        IndexModalComponent    
    ],
    selector: 'home-dashboard-compte-block',
    templateUrl: './compte.block.component.html'
})
export class CompteBlockComponent implements OnInit {
    
    etatComptes: EtatCompteBean[]

    totaux:number = 0

    constructor(
        private http: HttpClient,     
        private dialog: MatDialog) {

    }

    ngOnInit(): void {
        this.http        
            .get(`finance/compte/generate-etat-compte`)  
            .pipe(map((data: any) => data))
            .subscribe(data => {
                this.etatComptes = data.records;
                this.totaux = this.etatComptes.map(e => e.solde.value).reduce((a,b)=>a+b)
            });
    }
    
    compteAction() {
        let dialogRef =  this.dialog.open(IndexModalComponent);
        dialogRef.afterClosed().subscribe(result => {
            console.log(`TODO: Ne pas raffraichir si l'utilisateur n'a pas soumis le formulaire`)
        });  
    }
}
