package com.linkfield.softsim;

import com.linkfield.softsim.model.SoftSIMInfo;

interface ISoftSIMCallback {
    /**
     * Send Event to the client
     *
     * @param type
     *    SOFTSIM_EVENT_REFRESH = 1;
     *
     * @param subtype
     *    SOFTSIM_REFRESH_MANUAL = 0;
     *    SOFTSIM_REFRESH_EXPIRE = 1;
     *    SOFTSIM_REFRESH_EXCEED = 2;
     *
     * @return None
     */
    void onSoftSIMEvent(in int type, in int subtype);

    void onSoftSIMStateChange(in SoftSIMInfo info);
}
