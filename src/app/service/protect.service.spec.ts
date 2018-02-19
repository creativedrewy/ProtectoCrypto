import { TestBed, inject } from '@angular/core/testing';

import { ProtectService } from './protect.service';

describe('ProtectService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ProtectService]
    });
  });

  it('should be created', inject([ProtectService], (service: ProtectService) => {
    expect(service).toBeTruthy();
  }));
});
