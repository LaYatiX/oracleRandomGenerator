import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { IOsoba } from 'app/shared/model/osoba.model';
import { OsobaService } from './osoba.service';

@Component({
  selector: 'jhi-osoba',
  templateUrl: './osoba.component.html'
})
export class OsobaComponent implements OnInit, OnDestroy {
  osobas: IOsoba[];
  eventSubscriber: Subscription;

  constructor(protected osobaService: OsobaService, protected eventManager: JhiEventManager) {}

  loadAll() {
    this.osobaService.query().subscribe((res: HttpResponse<IOsoba[]>) => {
      this.osobas = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInOsobas();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IOsoba) {
    return item.id;
  }

  registerChangeInOsobas() {
    this.eventSubscriber = this.eventManager.subscribe('osobaListModification', () => this.loadAll());
  }
}
