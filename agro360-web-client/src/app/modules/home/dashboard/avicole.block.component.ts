import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Chart } from 'chart.js/auto';
import { map } from 'rxjs';
import { EtatRecetteDepenseBean } from 'src/app/backed/bean.finance';
import { SharedModule } from 'src/app/modules/common/shared.module';

@Component({
    standalone: true,
    imports: [
        SharedModule,     
    ],
    selector: 'home-dashboard-avicole-block',
    templateUrl: './avicole.block.component.html'
})
export class AvicoleBlockComponent implements OnInit {
    depenseVsRecette: any = [];
    ponte: any = [];
    deces: any = [];

    constructor(
        private http: HttpClient) {
    }

    private initDepenseVsRecetteChart(beans:Array<EtatRecetteDepenseBean>){
        
        this.depenseVsRecette = new Chart('depenseVsRecette', {
            type: 'bar',
            data: { 
                labels: beans.map(b => b.semaine.value),
                datasets: [
                    {
                        label: 'dépense',
                        data: beans.map(b => b.depense.value),
                        borderWidth: 1,
                        stack: 'Stack 0',
                        backgroundColor: 'red',
                    },
                    {
                        label: 'recette',
                        data: beans.map(b => b.recette.value),
                        borderWidth: 1,
                        stack: 'Stack 1',
                        backgroundColor: 'green',
                    },
                ],
            },
            options: {
                plugins: {
                    title: {
                      display: true,
                      text: 'Dépenses vs recettes'
                    },
                  },
                scales: {
                    x: {
                        stacked: true,
                    },
                    y: {
                        beginAtZero: true,
                        stacked: true,
                    },
                },
            },
        });
    }

    ngOnInit() {
        this.http        
            .get(`finance/transaction/generate-etat-recette-depense`)  
            .pipe(map((data: any) => data))
            .subscribe(data => {
                this.initDepenseVsRecetteChart(data.records);
            });
    }
}
