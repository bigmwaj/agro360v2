import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatToolbarModule } from '@angular/material/toolbar';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AchatModule } from './modules/achat/achat.module';
import { ProductionAvicoleModule } from './modules/production/avicole/production.avicole.module';
import { StockModule } from './modules/stock/stock.module';
import { TiersModule } from './modules/tiers/tiers.module';
import { VenteModule } from './modules/vente/vente.module';
import { UIService } from './common/service/ui.service';
import { PageTitleComponent } from './common/component/page-title.component';
import { CommonModule } from '@angular/common';

@NgModule({
    declarations: [
        AppComponent
    ],
    imports: [    
        MatIconModule,    
        MatButtonModule,        
        BrowserModule,
        AppRoutingModule,
        BrowserAnimationsModule,
        CommonModule,
        HttpClientModule,        
        MatSidenavModule,
        MatToolbarModule,
        MatListModule,
        TiersModule,
        StockModule,
        AchatModule,
        VenteModule,
        ProductionAvicoleModule,
        PageTitleComponent
    ],
    providers: [
        UIService
    ],
    exports:[],
    bootstrap: [AppComponent]
})
export class AppModule { }
