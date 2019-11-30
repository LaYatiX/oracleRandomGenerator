import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ITransakcja } from 'app/shared/model/transakcja.model';
import { TransakcjaService } from './transakcja.service';

@Component({
  selector: 'jhi-transakcja',
  templateUrl: './transakcja.component.html'
})
export class TransakcjaComponent implements OnInit, OnDestroy {
  transakcjas: ITransakcja[];
  eventSubscriber: Subscription;

  constructor(protected transakcjaService: TransakcjaService, protected eventManager: JhiEventManager) {}

  loadAll() {
    this.transakcjaService.query().subscribe((res: HttpResponse<ITransakcja[]>) => {
      this.transakcjas = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInTransakcjas();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ITransakcja) {
    return item.id;
  }

  registerChangeInTransakcjas() {
    this.eventSubscriber = this.eventManager.subscribe('transakcjaListModification', () => this.loadAll());
  }
}
