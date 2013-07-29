/*
 * android-spinnerwheel
 * https://github.com/ai212983/android-spinnerwheel
 *
 * based on
 *
 * Android Wheel Control.
 * https://code.google.com/p/android-wheel/
 *
 * Copyright 2011 Yuri Kanivets
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package antistatic.spinnerwheel.adapters;

import android.content.Context;

public class CategoryWheelAdapter extends AbstractWheelTextAdapter {

    private String items[];
    private boolean _matches[];
    private String _filter;

    public CategoryWheelAdapter(Context context, String items[]) {
        super(context);

        this.items = items;
        _matches = new boolean[items.length];
        setAllMatchesToTrue();
    }

    @Override
    public CharSequence getItemText(int index) {
        int count = calculateMatchesCount();
        if (count == 0)
            return _filter;

        index = getFilteredIndex(index);

        if (index >= 0 && index < items.length) {
            return items[index];
        }
        return null;
    }

    @Override
    public int getItemsCount() {
        int count = calculateMatchesCount();
        if (count > 0)
            return count;
        return 1;
    }

    public void setFilter(String filter)
    {
        if (filter == null)
            return;
        _filter = filter;

        setAllMatchesToTrue();

        for (int i = 0; i<items.length; i++ )
            if (!items[i].toLowerCase().contains(_filter.toLowerCase()))
                _matches[i] = false;
    }

    private void setAllMatchesToTrue() {
        for (int i = 0; i<_matches.length; i++)
            _matches[i] = true;
    }

    private int calculateMatchesCount()
    {
        int count = 0;
        for (boolean match : _matches)
            if (match) count++;

        return count;
    }

    private int getFilteredIndex(int index)
    {
        int count = 0;
        int i = 0;
        while (count < index + 1)
        {
            if (_matches[i])
                count++;
            i++;
        }

        return --i;
    }

    public int getRealCategoryIndex(int index)
    {
        int count = calculateMatchesCount();
        if (count == 0)
            return -1;

        return getFilteredIndex(index);
    }
}
