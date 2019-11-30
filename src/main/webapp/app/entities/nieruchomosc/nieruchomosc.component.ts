import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { INieruchomosc } from 'app/shared/model/nieruchomosc.model';
import { NieruchomoscService } from './nieruchomosc.service';

@Component({
  selector: 'jhi-nieruchomosc',
  templateUrl: './nieruchomosc.component.html'
})
export class NieruchomoscComponent implements OnInit, OnDestroy {
  nieruchomoscs: INieruchomosc[];
  eventSubscriber: Subscription;

  constructor(protected nieruchomoscService: NieruchomoscService, protected eventManager: JhiEventManager) {}

  loadAll() {
    this.nieruchomoscService.query().subscribe((res: HttpResponse<INieruchomosc[]>) => {
      this.nieruchomoscs = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInNieruchomoscs();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: INieruchomosc) {
    return item.id;
  }

  registerChangeInNieruchomoscs() {
    this.eventSubscriber = this.eventManager.subscribe('nieruchomoscListModification', () => this.loadAll());
  }
}
