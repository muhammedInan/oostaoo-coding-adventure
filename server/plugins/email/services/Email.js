"use strict";

/**
 * Email.js service
 *
 * @description: A set of functions similar to controller's actions to avoid code duplication.
 */

const _ = require("lodash");

const nodemailer = require("nodemailer");

const crypto = require("crypto");

// Create reusable transporter object using SMTP transport.
const transporter = nodemailer.createTransport({
  service: "Gmail",
  auth: {
    user: "lenoir.jeremie@oostaoo.com",
    pass: "marijuana"
  }
});

const createDefaultEnvConfig = async env => {
  const pluginStore = strapi.store({
    environment: env,
    type: "plugin",
    name: "email"
  });

  const provider = _.find(strapi.plugins.email.config.providers, {
    provider: "sendmail"
  });
  const value = _.assign({}, provider, {});

  await pluginStore.set({ key: "provider", value });
  return await strapi
    .store({
      environment: env,
      type: "plugin",
      name: "email"
    })
    .get({ key: "provider" });
};

const getProviderConfig = async env => {
  let config = await strapi
    .store({
      environment: env,
      type: "plugin",
      name: "email"
    })
    .get({ key: "provider" });

  if (!config) {
    config = await createDefaultEnvConfig(env);
  }

  return config;
};

module.exports = {
  getProviderConfig,
  send: async (options, config, cb) => {
    console.log("options SEND : ", options);
    try {
      const optionsData = {
        to: options.to,
        from: options.from,
        replyTo: options.replyTo,
        subject: "Réinitialisation du mot de passe ",
        html: options.html
      };
      await transporter.sendMail(optionsData);
      return "ok";
    } catch (e) {
      return null;
    }

    // // Get email provider settings to configure the provider to use.
    // if (!config) {
    //   config = await getProviderConfig(strapi.config.environment);
    // }

    // const provider = _.find(strapi.plugins.email.config.providers, {
    //   provider: config.provider
    // });

    // if (!provider) {
    //   throw new Error(
    //     `The provider package isn't installed. Please run \`npm install strapi-provider-email-${config.provider}\``
    //   );
    // }

    // const actions = provider.init(config);

    // // Execute email function of the provider for all files.
    // return actions.send(options, cb);
  }
};