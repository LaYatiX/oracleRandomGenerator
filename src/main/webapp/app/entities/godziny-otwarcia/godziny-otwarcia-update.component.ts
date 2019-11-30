import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IGodzinyOtwarcia, GodzinyOtwarcia } from 'app/shared/model/godziny-otwarcia.model';
import { GodzinyOtwarciaService } from './godziny-otwarcia.service';

@Component({
  selector: 'jhi-godziny-otwarcia-update',
  templateUrl: './godziny-otwarcia-update.component.html'
})
export class GodzinyOtwarciaUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    poniedzialek: [],
    wtorek: [],
    sroda: [],
    czwartek: [],
    piatek: [],
    sobota: [],
    niedziela: []
  });

  constructor(
    protected godzinyOtwarciaService: GodzinyOtwarciaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ godzinyOtwarcia }) => {
      this.updateForm(godzinyOtwarcia);
    });
  }

  updateForm(godzinyOtwarcia: IGodzinyOtwarcia) {
    this.editForm.patchValue({
      id: godzinyOtwarcia.id,
      poniedzialek: godzinyOtwarcia.poniedzialek,
      wtorek: godzinyOtwarcia.wtorek,
      sroda: godzinyOtwarcia.sroda,
      czwartek: godzinyOtwarcia.czwartek,
      piatek: godzinyOtwarcia.piatek,
      sobota: godzinyOtwarcia.sobota,
      niedziela: godzinyOtwarcia.niedziela
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const godzinyOtwarcia = this.createFromForm();
    if (godzinyOtwarcia.id !== undefined) {
      this.subscribeToSaveResponse(this.godzinyOtwarciaService.update(godzinyOtwarcia));
    } else {
      this.subscribeToSaveResponse(this.godzinyOtwarciaService.create(godzinyOtwarcia));
    }
  }

  private createFromForm(): IGodzinyOtwarcia {
    return {
      ...new GodzinyOtwarcia(),
      id: this.editForm.get(['id']).value,
      poniedzialek: this.editForm.get(['poniedzialek']).value,
      wtorek: this.editForm.get(['wtorek']).value,
      sroda: this.editForm.get(['sroda']).value,
      czwartek: this.editForm.get(['czwartek']).value,
      piatek: this.editForm.get(['piatek']).value,
      sobota: this.editForm.get(['sobota']).value,
      niedziela: this.editForm.get(['niedziela']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGodzinyOtwarcia>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
