package com.minhnv.luxuryhomestay.ui.base;

import android.content.Context;

public interface ViewModelType<I,O> {
    O transform(Context context, I input);
}
