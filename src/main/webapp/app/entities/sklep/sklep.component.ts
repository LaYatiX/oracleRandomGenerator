import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ISklep } from 'app/shared/model/sklep.model';
import { SklepService } from './sklep.service';

@Component({
  selector: 'jhi-sklep',
  templateUrl: './sklep.component.html'
})
export class SklepComponent implements OnInit, OnDestroy {
  skleps: ISklep[];
  eventSubscriber: Subscription;

  constructor(protected sklepService: SklepService, protected eventManager: JhiEventManager) {}

  loadAll() {
    this.sklepService.query().subscribe((res: HttpResponse<ISklep[]>) => {
      this.skleps = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInSkleps();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ISklep) {
    return item.id;
  }

  registerChangeInSkleps() {
    this.eventSubscriber = this.eventManager.subscribe('sklepListModification', () => this.loadAll());
  }
}
