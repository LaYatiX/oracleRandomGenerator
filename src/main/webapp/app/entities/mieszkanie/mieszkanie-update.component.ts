import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IMieszkanie, Mieszkanie } from 'app/shared/model/mieszkanie.model';
import { MieszkanieService } from './mieszkanie.service';
import { INieruchomosc } from 'app/shared/model/nieruchomosc.model';
import { NieruchomoscService } from 'app/entities/nieruchomosc/nieruchomosc.service';

@Component({
  selector: 'jhi-mieszkanie-update',
  templateUrl: './mieszkanie-update.component.html'
})
export class MieszkanieUpdateComponent implements OnInit {
  isSaving: boolean;

  nieruchomoscs: INieruchomosc[];

  editForm = this.fb.group({
    id: [],
    metraz: [],
    czyLazienka: [],
    czyKuchnia: [],
    czyWyposazone: [],
    nieruchomosc: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected mieszkanieService: MieszkanieService,
    protected nieruchomoscService: NieruchomoscService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ mieszkanie }) => {
      this.updateForm(mieszkanie);
    });
    this.nieruchomoscService
      .query()
      .subscribe(
        (res: HttpResponse<INieruchomosc[]>) => (this.nieruchomoscs = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(mieszkanie: IMieszkanie) {
    this.editForm.patchValue({
      id: mieszkanie.id,
      metraz: mieszkanie.metraz,
      czyLazienka: mieszkanie.czyLazienka,
      czyKuchnia: mieszkanie.czyKuchnia,
      czyWyposazone: mieszkanie.czyWyposazone,
      nieruchomosc: mieszkanie.nieruchomosc
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const mieszkanie = this.createFromForm();
    if (mieszkanie.id !== undefined) {
      this.subscribeToSaveResponse(this.mieszkanieService.update(mieszkanie));
    } else {
      this.subscribeToSaveResponse(this.mieszkanieService.create(mieszkanie));
    }
  }

  private createFromForm(): IMieszkanie {
    return {
      ...new Mieszkanie(),
      id: this.editForm.get(['id']).value,
      metraz: this.editForm.get(['metraz']).value,
      czyLazienka: this.editForm.get(['czyLazienka']).value,
      czyKuchnia: this.editForm.get(['czyKuchnia']).value,
      czyWyposazone: this.editForm.get(['czyWyposazone']).value,
      nieruchomosc: this.editForm.get(['nieruchomosc']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMieszkanie>>) {
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

  trackNieruchomoscById(index: number, item: INieruchomosc) {
    return item.id;
  }
}
