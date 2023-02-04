package com.mindex.challenge.service;

import com.mindex.challenge.data.Compensation;

public interface CompensationService {
    public Compensation read(String id);

   public Compensation create(Compensation compensation);
}
