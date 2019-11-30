import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ILokalizacja } from 'app/shared/model/lokalizacja.model';
import { LokalizacjaService } from './lokalizacja.service';

@Component({
  selector: 'jhi-lokalizacja',
  templateUrl: './lokalizacja.component.html'
})
export class LokalizacjaComponent implements OnInit, OnDestroy {
  lokalizacjas: ILokalizacja[];
  eventSubscriber: Subscription;

  constructor(protected lokalizacjaService: LokalizacjaService, protected eventManager: JhiEventManager) {}

  loadAll() {
    this.lokalizacjaService.query().subscribe((res: HttpResponse<ILokalizacja[]>) => {
      this.lokalizacjas = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInLokalizacjas();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ILokalizacja) {
    return item.id;
  }

  registerChangeInLokalizacjas() {
    this.eventSubscriber = this.eventManager.subscribe('lokalizacjaListModification', () => this.loadAll());
  }
}
