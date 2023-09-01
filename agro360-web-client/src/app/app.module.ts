import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { HttpClientModule } from '@angular/common/http';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatSelectModule } from '@angular/material/select';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatListModule } from '@angular/material/list';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';

import { AppComponent } from './app.component';

import { PageTitleComponent } from './common/page-title.component';
import { TiersModule } from './modules/tiers/tiers.module';
import { CommonModule } from '@angular/common';
import { StockModule } from './modules/stock/stock.module';
import { AchatModule } from './modules/achat/achat.module';
import { VenteModule } from './modules/vente/vente.module';
import { ProductionAvicoleModule } from './modules/production/avicole/production.avicole.module';

@NgModule({
    declarations: [
        AppComponent,
        PageTitleComponent
    ],
    imports: [    
        MatIconModule,    
        MatButtonModule,
        
        BrowserModule,
        AppRoutingModule,
        BrowserAnimationsModule,

        HttpClientModule,        
        MatSidenavModule,
        MatToolbarModule,
        MatListModule,

        TiersModule,
        StockModule,
        AchatModule,
        VenteModule,
        ProductionAvicoleModule
    ],
    providers: [
    ],
    exports:[],
    bootstrap: [AppComponent]
})
export class AppModule { }
