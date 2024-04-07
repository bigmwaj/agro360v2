import { Component, OnInit } from '@angular/core';
import { Chart } from 'chart.js/auto';
import { SharedModule } from 'src/app/common/shared.module';

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
    ngOnInit() {
        this.depenseVsRecette = new Chart('depenseVsRecette', {
            type: 'bar',
            data: { 
                labels: ['SEM 4', 'SEM 3', 'SEM 2', 'SEM 1'],
                datasets: [
                    {
                        label: 'dépense',
                        data: [12, 19, 3, 5],
                        borderWidth: 1,
                        stack: 'Stack 0',
                        backgroundColor: 'red',
                    },
                    {
                        label: 'recette',
                        data: [12, 40, 3, 5],
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

        this.ponte = new Chart('ponte', {
            type: 'bar',
            data: { 
                labels: ['Lun', 'Mar', 'Mer', 'Jeu', 'Ven', 'Sam', 'Dim'],
                datasets: [
                    {
                        label: 'oeufs',
                        data: [12, 19, 3, 5, 20, 15, 18],
                        borderWidth: 1,
                        stack: 'Stack 0',
                        backgroundColor: 'yellow',
                    },
                ],
            },
            options: {
                plugins: {
                    title: {
                      display: true,
                      text: 'Évolution de la ponte'
                    },
                  },
                scales: {
                    y: {
                        beginAtZero: true,
                    },
                },
            },
        });

        this.deces = new Chart('deces', {
            type: 'bar',
            data: { 
                labels: ['Lun', 'Mar', 'Mer', 'Jeu', 'Ven', 'Sam', 'Dim'],
                datasets: [
                    {
                        label: 'sujet',
                        data: [12, 19, 3, 5, 6, 7, 9],
                        borderWidth: 1,
                        stack: 'Stack 0',
                        backgroundColor: 'blue',
                    },
                ],
            },
            options: {
                plugins: {
                    title: {
                      display: true,
                      text: 'Décès'
                    },
                  },
                scales: {
                    y: {
                        beginAtZero: true,
                    },
                },
            },
        });


    }

}
