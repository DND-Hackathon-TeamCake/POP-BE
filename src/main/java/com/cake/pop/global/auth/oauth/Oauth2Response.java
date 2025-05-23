package com.cake.pop.global.auth.oauth;

public interface Oauth2Response {
	String getProvider();

	String getProviderId();

	String getEmail();

	String getName();

	String createSocialEmail();

	String getOauth2AccessToken();
}
