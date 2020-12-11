package com.example.translatorapp.utils;

import io.reactivex.rxjava3.core.Scheduler;

public interface ISchedulerProvider {
    Scheduler ui();
    Scheduler io();
}