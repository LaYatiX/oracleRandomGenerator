import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { ITransakcja, Transakcja } from 'app/shared/model/transakcja.model';
import { TransakcjaService } from './transakcja.service';
import { ISklep } from 'app/shared/model/sklep.model';
import { SklepService } from 'app/entities/sklep/sklep.service';

@Component({
  selector: 'jhi-transakcja-update',
  templateUrl: './transakcja-update.component.html'
})
export class TransakcjaUpdateComponent implements OnInit {
  isSaving: boolean;

  skleps: ISklep[];

  editForm = this.fb.group({
    id: [],
    nettoo: [],
    brutton: [],
    vat: [],
    sklep: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected transakcjaService: TransakcjaService,
    protected sklepService: SklepService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ transakcja }) => {
      this.updateForm(transakcja);
    });
    this.sklepService
      .query()
      .subscribe((res: HttpResponse<ISklep[]>) => (this.skleps = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(transakcja: ITransakcja) {
    this.editForm.patchValue({
      id: transakcja.id,
      nettoo: transakcja.nettoo,
      brutton: transakcja.brutton,
      vat: transakcja.vat,
      sklep: transakcja.sklep
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const transakcja = this.createFromForm();
    if (transakcja.id !== undefined) {
      this.subscribeToSaveResponse(this.transakcjaService.update(transakcja));
    } else {
      this.subscribeToSaveResponse(this.transakcjaService.create(transakcja));
    }
  }

  private createFromForm(): ITransakcja {
    return {
      ...new Transakcja(),
      id: this.editForm.get(['id']).value,
      nettoo: this.editForm.get(['nettoo']).value,
      brutton: this.editForm.get(['brutton']).value,
      vat: this.editForm.get(['vat']).value,
      sklep: this.editForm.get(['sklep']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITransakcja>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackSklepById(index: number, item: ISklep) {
    return item.id;
  }
}
