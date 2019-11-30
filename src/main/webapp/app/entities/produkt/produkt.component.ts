import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { IProdukt } from 'app/shared/model/produkt.model';
import { ProduktService } from './produkt.service';

@Component({
  selector: 'jhi-produkt',
  templateUrl: './produkt.component.html'
})
export class ProduktComponent implements OnInit, OnDestroy {
  produkts: IProdukt[];
  eventSubscriber: Subscription;

  constructor(protected produktService: ProduktService, protected eventManager: JhiEventManager) {}

  loadAll() {
    this.produktService.query().subscribe((res: HttpResponse<IProdukt[]>) => {
      this.produkts = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInProdukts();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IProdukt) {
    return item.id;
  }

  registerChangeInProdukts() {
    this.eventSubscriber = this.eventManager.subscribe('produktListModification', () => this.loadAll());
  }
}
