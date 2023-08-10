import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { HttpClientModule } from '@angular/common/http';
import { TiersModule } from './modules/tiers/tiers.module';
import { StockModule } from './modules/stock/stock.module';
import { AchatModule } from './modules/achat/achat.module';
import { VenteModule } from './modules/vente/vente.module';
import { ProductionAvicoleModule } from './modules/production/avicole/production.avicole.module';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    MatSlideToggleModule,
    MatButtonModule,
    BrowserAnimationsModule,
    MatInputModule,
    HttpClientModule,
    TiersModule,
    StockModule,
    AchatModule,
    VenteModule,
    ProductionAvicoleModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
