import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { IMieszkanie } from 'app/shared/model/mieszkanie.model';
import { MieszkanieService } from './mieszkanie.service';

@Component({
  selector: 'jhi-mieszkanie',
  templateUrl: './mieszkanie.component.html'
})
export class MieszkanieComponent implements OnInit, OnDestroy {
  mieszkanies: IMieszkanie[];
  eventSubscriber: Subscription;

  constructor(protected mieszkanieService: MieszkanieService, protected eventManager: JhiEventManager) {}

  loadAll() {
    this.mieszkanieService.query().subscribe((res: HttpResponse<IMieszkanie[]>) => {
      this.mieszkanies = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInMieszkanies();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IMieszkanie) {
    return item.id;
  }

  registerChangeInMieszkanies() {
    this.eventSubscriber = this.eventManager.subscribe('mieszkanieListModification', () => this.loadAll());
  }
}
