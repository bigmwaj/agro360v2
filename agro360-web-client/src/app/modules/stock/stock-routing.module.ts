import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { EditPageComponent as ArticleEditPageComponent } from './article/edit.page.component';
import { IndexPageComponent as ArticleIndexPageComponent } from './article/index.page.component';

import { EditPageComponent as MagasinEditPageComponent } from './magasin/edit.page.component';
import { IndexPageComponent as MagasinIndexPageComponent } from './magasin/index.page.component';
import { IndexPageComponent as CaisseIndexPageComponent } from './caisse/index.page.component';
import { EditPageComponent as CaisseEditPageComponent } from './caisse/edit.page.component';

const routes: Routes = [
    { path: 'stock/article', component: ArticleIndexPageComponent },
    { path: 'stock/article/index', component: ArticleIndexPageComponent },
    { path: 'stock/article/create', component: ArticleEditPageComponent },
    { path: 'stock/article/edit/:articleCode', component: ArticleEditPageComponent },

    { path: 'stock/magasin', component: MagasinIndexPageComponent },
    { path: 'stock/magasin/index', component: MagasinIndexPageComponent },
    { path: 'stock/magasin/create', component: MagasinEditPageComponent },
    { path: 'stock/magasin/edit/:magasinCode', component: MagasinEditPageComponent },
    
    { path: 'stock/caisse', component: CaisseIndexPageComponent },
    { path: 'stock/caisse/index', component: CaisseIndexPageComponent },
    { path: 'stock/caisse/create', component: CaisseEditPageComponent },
    { path: 'stock/caisse/edit/:magasin/:Partner/:journee', component: CaisseEditPageComponent },
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class StockRoutingModule { }
