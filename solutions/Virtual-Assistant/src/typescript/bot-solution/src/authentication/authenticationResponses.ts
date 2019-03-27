/**
 * Copyright(c) Microsoft Corporation.All rights reserved.
 * Licensed under the MIT License.
 */

import { IResponseIdCollection } from '../responses/responseIdCollection';

export class AuthenticationResponses implements IResponseIdCollection {
    // Generated accessors
    public readonly name: string = AuthenticationResponses.name;
    public static readonly pathToResource: string = __dirname;
    public static readonly skillAuthenticationTitle: string = 'SkillAuthenticationTitle';
    public static readonly skillAuthenticationPrompt: string = 'SkillAuthenticationPrompt';
    public static readonly authProvidersPrompt: string = 'AuthProvidersPrompt';
    public static readonly configuredAuthProvidersPrompt: string = 'ConfiguredAuthProvidersPrompt';
    public static readonly errorMessageAuthFailure: string = 'ErrorMessageAuthFailure';
}
