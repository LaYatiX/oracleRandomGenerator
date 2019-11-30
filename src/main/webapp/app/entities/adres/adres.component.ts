import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { IAdres } from 'app/shared/model/adres.model';
import { AdresService } from './adres.service';

@Component({
  selector: 'jhi-adres',
  templateUrl: './adres.component.html'
})
export class AdresComponent implements OnInit, OnDestroy {
  adres: IAdres[];
  eventSubscriber: Subscription;

  constructor(protected adresService: AdresService, protected eventManager: JhiEventManager) {}

  loadAll() {
    this.adresService.query().subscribe((res: HttpResponse<IAdres[]>) => {
      this.adres = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInAdres();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IAdres) {
    return item.id;
  }

  registerChangeInAdres() {
    this.eventSubscriber = this.eventManager.subscribe('adresListModification', () => this.loadAll());
  }
}
